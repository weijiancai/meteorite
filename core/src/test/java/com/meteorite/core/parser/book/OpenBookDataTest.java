/**
 * 2013-4-12
 */
package com.meteorite.core.parser.book;

import org.junit.Test;

import java.text.ParseException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wang_liang
 * @Date 2013-4-12 下午01:28:52
 * @since 1.0
 */
public class OpenBookDataTest {

	@Test
	public void testParser() throws Exception {
		OpenBookDataParser parser = new OpenBookDataParser(3328918);
		List<IWebProduct> list = parser.parse();
        assertThat(list.size(), equalTo(1));
		IWebProduct prod = list.get(0);
		System.out.println(prod);
		
		List<ProductPic> picList = prod.getProductPic();
		assertThat(picList.size(), equalTo(1));
		assertThat(picList.get(0).getUrl().toString(), equalTo("http://image-1.openbook.com.cn/BookCover/20140328/3324753.jpg"));
		
		assertThat(prod.getSourceSite(), equalTo(SiteName.OPEN_BOOK_DATA.name()));
		assertThat(prod.getName(), equalTo("记得自己:第四道传统精选"));
		assertThat(prod.getAuthor(), equalTo("葛吉夫,邬斯宾斯基"));
		assertThat(prod.getPrice(), equalTo("36.00"));
		assertThat(prod.getPublishing(), equalTo("中央编译出版社"));
		assertThat(prod.getPublishDate(), equalTo("2014-03"));
		assertThat(prod.getIsbn(), equalTo("9787511720610"));
		assertThat(prod.getBanci(), equalTo("第1版"));
		assertThat(prod.getKaiben(), equalTo("16开"));
		assertThat(prod.getPageNum(), equalTo("168"));
		assertThat(prod.getPack(), equalTo("平装"));
        assertThat(prod.getTranslator(), equalTo("王君永"));
        assertThat(prod.getWordCount(), equalTo("123.0"));
        String media = "传统之路，如佛教、基督教、伊斯兰教，以及印度教，已广为人知，世所公认。第四道作为一个古老的传统已经存在很久了，却鲜有人知，仅以外在形式被隐晦地表述过。所有这些道路都指往同一个方向：最终的觉醒, 禅境或悟道。我们对第四道了解越多，就越能认识和理解其它道路的内在意义。第四道在历史中的早期表述并不自称为“第四道”。葛吉夫为了与三种传统修行方式：苦行者，僧侣和瑜伽修行者（分别侧重于身体、情感和理智方面的发展）相区分，把他的介绍称为“第四道”。第四道是一种基于日常生活的修行体系，容纳了不同传统的秘义教学，并把当下的生活状况看作自我发展的平台；第四道是一条实用的道，人只有通过切身体验才能理解，并带来意识的真正改变。“记得自己”一词译自英文“Self-Remembering”，是第四道传统的核心观念及实践方法。记得自己是跨越知识和智慧的桥梁。这是一种在此刻觉察到自己的努力，脱离一个人在前一刻陷入的想象世界而回到现实。这是一种即刻的内在重组：将自己的机械念头和情感抛下，并使自己的高等自我显现——记得自己。 “第四道传统精选”是第四道精要作品的合集。作品摘选自五位第四道老师，他们从二十世纪初到现在一脉相承。因为第四道基于个人的验证、理解以及口传身教，每一位老师都以其独特的方式来阐释内在工作的本质。这也体现了第四道历久弥新。";
        assertThat(prod.getMediaFeedback(), equalTo(media));
        String content = "葛吉夫为了与三种传统修行方式——苦行者、僧侣和瑜伽修行者相区分，把自己的修行方式称之为“第四道”。第四道是一种基于日常生活的修行体系，容纳了不同传统的秘义教学，并把当下的生活状况看作自我发展的平台；人只有通过切身体验才能理解，并带来意识的真正改变。“记得自己”一词译自英文“Self-Remembering”，是第四道传统的核心观念及实践方法。 作为一个古老的传统已经存在很久， 所有这些道路都指往同一个方向：最终的觉醒, 禅境或悟道。本书是第四道精要作品的合集，作品摘选自葛吉夫及其追随者等人物的作品。";
        assertThat(prod.getContent(), equalTo(content));
        String author = "葛吉夫（G. I. Gurdjieff ,1866～ 1949），曾经游学许多古老密意知识流传的地域，包括印度、西藏、埃及、麦加、苏丹、伊拉克，前半生如同一阕隐讳的神谕，没有人知晓他的真实来历、修学背景。\r" +
                " 1912年在莫斯科与圣彼得堡成立修行团体，1917年俄国大革命风暴席卷每一个地方，1920年葛吉夫率领弟子逃出俄国，暂时落脚在土耳其的君士坦丁堡，成立修行机构。 1922年 定居 法国巴黎 枫丹白露 。\r" +
                " 葛吉夫宣称“第四道”并非他自己发明的，而是渊自久远的古老智慧。我们可以在第四道体系中看到有些理念脱胎于佛教、苏菲密教、基督教，有些理念则是原创性的 。葛吉夫博杂广大的密意知识经由大弟子邬斯宾斯基整理后， 体系更加条理分明。";
        assertThat(prod.getAuthorIntro(), equalTo(author));
        String dirctory = "目 录序一）葛吉夫的格言 葛吉夫简介 格言二）论记得自己——邬斯宾斯基思想集 邬斯宾斯基简介 第一章 我的第一次尝试第二章 游移在两岸间第三章 最有趣的观念第四章 定义不会有帮助第五章 意识到不记得第六章 机械的人第七章 辨别意识与功能第八章 记得自己就是做第九章 人是一个化学工厂第十章 开始看到第十一章 第四道，学校，和有意识的影响第十二章 我教你记得自己三）有意识的和谐——郎尼·克林书信摘选 克林简介 1．第四道 2．心理工作和内在努力的正确态度 3．培养真诚而善解的心 4．记得自己 5．障碍（自我重要感，想象，不必要的谈话和说谎，负面情绪） 6．消除障碍的方法（接受，不执着，转化痛苦） 7．代价 8．丙种影响 9．学校传统 10．教学 11．邬斯宾斯基与葛吉夫 12．死亡与转化 四）有意识教学的笔记 吉拉德简介 1．机器 2．群‘我' 3．活在当下五）每日一思 罗伯特简介第四道知识和素质低等自我（想象，本能中心，认同，负面情绪）努力控制激情分开注意力记得自己转化痛苦序列当下有意识的爱本质单纯时间死亡学校丙种影响词汇表读者反馈";
        assertThat(prod.getCatalog(), equalTo(dirctory));
    }


}
